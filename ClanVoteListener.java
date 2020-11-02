import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VoteListener;
import com.jaeheonshim.simplysurvival.mclans.ClanManager;

public class ClanVoteListener implements VoteListener {
    public void voteMade(Vote vote) {
        String username = vote.getUsername();
        ClanManager.getClanManager().handleVote(username);
    }
}