package com.jaeheonshim.simplysurvival.mclans;

import java.time.Duration;

public class Constant {
    public static final long CLAN_DESTROY_CONFIRM_TIMEOUT = Duration.ofSeconds(15).toMillis();
    public static final long CLAN_EARN_CLAIM_TIME = Duration.ofHours(5).toMillis();
    public static final int CLAN_VOTE_LAND_AMOUNT = 15;
}
