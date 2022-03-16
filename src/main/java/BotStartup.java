import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class BotStartup {
    public static void main(String[] args) throws LoginException {
        File file = new File("E:\\Dev\\JennyChan\\.env");
        Scanner token = null;
        try {
            token = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Token file is not found.");
            System.exit(1);
        }
        JDABuilder jda = JDABuilder.createDefault(token.next());
        jda.setStatus(OnlineStatus.ONLINE);
        jda.setActivity(Activity.watching("hentai"));
        jda.addEventListeners(new BotCommands());
        jda.setChunkingFilter(ChunkingFilter.ALL);
        jda.setMemberCachePolicy(MemberCachePolicy.ALL);
        jda.enableIntents(GatewayIntent.GUILD_MEMBERS);
        jda.build();
    }
}