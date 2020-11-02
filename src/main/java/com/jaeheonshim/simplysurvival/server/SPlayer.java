package com.jaeheonshim.simplysurvival.server;

import com.jaeheonshim.simplysurvival.mclans.Clan;
import com.jaeheonshim.simplysurvival.mclans.ClanInvitation;
import com.jaeheonshim.simplysurvival.mclans.Constant;
import com.jaeheonshim.simplysurvival.server.domain.MessageSequence;
import com.jaeheonshim.simplysurvival.server.domain.PlayerProperties;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.*;

@Entity
public class SPlayer {
    @Id
    private String _id;
    private long timePlayed;
    private String cachedUsername;

    private String muteReason;
    private long mutedUntil;
    private boolean completedIntro;

    private Map<PlayerProperties, String> properties = new HashMap<>();

    @Transient
    private long destroyClanTimeStamp;

    @Transient
    private long lastDamageTime;

    @Transient
    private long lastPvpTime;

    @Transient
    private MessageSequence currentIntroSequence = Constants.welcomeSequence;

    @Transient
    private Set<ClanInvitation> invitations = new HashSet<>();

    public SPlayer() {

    }

    public void addTimePlayed(long minutes) {
        timePlayed += minutes;
    }

    public String getCachedUsername() {
        return cachedUsername;
    }

    public void setCachedUsername(String cachedUsername) {
        this.cachedUsername = cachedUsername;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public String getUuid() {
        return _id;
    }

    public void setUuid(String uuid) {
        this._id = uuid;
    }

    public void addInvitation(ClanInvitation invitation) {
        invitations.add(invitation);
    }

    public boolean isInvited(Clan clan) {
        for(ClanInvitation invitation : invitations) {
            if(invitation.getClanId().equalsIgnoreCase(clan.getId().toString())) {
                return true;
            }
        }
        return false;
    }

    public ClanInvitation getInvitation(Clan clan) {
        for(ClanInvitation invitation : invitations) {
            if(invitation.getClanId().equalsIgnoreCase(clan.getId().toString())) {
                return invitation;
            }
        }
        return null;
    }

    public void removeInvitation(Clan clan) {
        Iterator<ClanInvitation> invitationIterator = invitations.iterator();
        while(invitationIterator.hasNext()) {
            if(invitationIterator.next().getClanId().equals(clan.getId().toString())) {
                invitationIterator.remove();
            }
        }
    }

    public boolean isConfirmDestroy() {
        return destroyClanTimeStamp + Constant.CLAN_DESTROY_CONFIRM_TIMEOUT >= System.currentTimeMillis();
    }

    public void queueDestroy() {
        destroyClanTimeStamp = System.currentTimeMillis();
    }

    public PlayerFlag[] getPlayerFlags() {
        List<PlayerFlag> flags = new ArrayList<>();
        if (Bukkit.getPlayer(UUID.fromString(getUuid())).hasPermission("simplysurvival.admin")) {
            flags.add(new PlayerFlag(0, ChatColor.RED, "ADMIN"));
        }

        return flags.toArray(new PlayerFlag[0]);
    }

    public String getPrettyFlags() {
        StringBuilder flagBuilder = new StringBuilder();
        PlayerFlag[] flags = getPlayerFlags();
        Arrays.sort(flags, PlayerFlag.byPriority);

        for(PlayerFlag flag : flags) {
            flagBuilder.append(flag.getChatColor());
            flagBuilder.append("[");
            flagBuilder.append(flag.getName());
            flagBuilder.append("]");
            flagBuilder.append(ChatColor.RESET);
        }

        return flagBuilder.toString();
    }

    public void setMutedDuration(Duration duration, String muteReason) {
        this.muteReason = muteReason;
        mutedUntil = System.currentTimeMillis() + duration.toMillis();
    }

    public void unmute() {
        mutedUntil = 0;
        muteReason = null;
    }

    public boolean isMuted() {
        return mutedUntil > System.currentTimeMillis();
    }

    public long getMutedUntil() {
        return mutedUntil;
    }

    public String getMuteReason() {
        return muteReason;
    }

    public boolean isCompletedIntro() {
        return completedIntro;
    }

    public MessageSequence getCurrentIntroSequence() {
        return currentIntroSequence;
    }

    public void setCurrentIntroSequence(MessageSequence currentIntroSequence) {
        this.currentIntroSequence = currentIntroSequence;
    }

    public void setCompletedIntro(boolean completedIntro) {
        this.completedIntro = completedIntro;
    }

    public boolean isEnabledPvp() {
        return Boolean.parseBoolean(properties.get(PlayerProperties.ENABLED_PVP));
    }

    public void saveProperty(PlayerProperties property, String value) {
        this.properties.put(property, value);
    }

    public void saveProperty(PlayerProperties property, boolean value) {
        this.properties.put(property, Boolean.toString(value));
    }

    public String getProperty(PlayerProperties property) {
        return this.properties.getOrDefault(property, "");
    }

    public void recordDamage() {
        lastDamageTime = System.currentTimeMillis();
    }

    public boolean isRecallEligible() {
        return System.currentTimeMillis() - lastDamageTime >= Duration.ofSeconds(15).toMillis();
    }

    public boolean isLog() {
        return System.currentTimeMillis() - lastPvpTime <= Constants.PVP_LOG_THRESHOLD;
    }
}
