import javax.security.auth.login.LoginException;

import BotCommands.CommandsList;
import BotCommands.Moderation.DeleteCommand;
import BotCommands.Moderation.EditCommand;
import BotCommands.Moderation.GiveroleCommand;
import BotCommands.Moderation.KickCommand;
import BotCommands.Moderation.RemoveroleCommand;
import BotCommands.Moderation.ReplyCommand;
import BotCommands.Moderation.SayCommand;
import BotCommands.Utilities.AvatarCommand;
import BotCommands.Utilities.MessageCommand;
import BotCommands.Utilities.PingCommand;
import BotCommands.Utilities.ReactCommand;
import BotCommands.Utilities.ServerinfoCommand;
import BotCommands.Utilities.ShutdownCommand;
import BotCommands.Utilities.UptimeCommand;
import BotCommands.Utilities.UserinfoCommand;
import MentionCommands.SelfMention;

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

        jda.addEventListeners(
            new CommandsList(),
            
            new UserinfoCommand(),
            new UptimeCommand(),
            new ShutdownCommand(),
            new ServerinfoCommand(),
            new ReactCommand(),
            new PingCommand(),
            new MessageCommand(),
            new AvatarCommand(),
            new SayCommand(),
            new ReplyCommand(),
            new RemoveroleCommand(),
            new KickCommand(),
            new GiveroleCommand(),
            new EditCommand(),
            new DeleteCommand(),

            new SelfMention()
            );
        
        jda.setChunkingFilter(ChunkingFilter.ALL);
        jda.setMemberCachePolicy(MemberCachePolicy.ALL);
        jda.enableIntents(GatewayIntent.GUILD_MEMBERS);
        jda.build();
    }
}