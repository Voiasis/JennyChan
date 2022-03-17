import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MentionCommands extends ListenerAdapter {
    public String prefix = "!"; //bot prefix
    public String botID = "<@!952761165577060453>"; //bot mention ID
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args[0].equalsIgnoreCase(botID)) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setTitle("Hey cutie~ ;3", null);
            embed.setDescription("Say \"" + prefix + "commands\" to get started!");
            embed.setFooter(ftr, avURL);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
    } //TODO add all commands
}