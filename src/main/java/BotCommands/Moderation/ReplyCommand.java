package BotCommands.Moderation;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ReplyCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        
        if (args[0].equalsIgnoreCase(prefix + "reply")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

            if (event.getMessage().getContentRaw().toCharArray().length >= 7) {
                Message msg = event.getMessage().getMessageReference().getMessage();
                msg.reply(event.getMessage().getContentRaw().substring(7)).queue();
                event.getMessage().delete().queue();
            } else {
                embed.setTitle(prefix + "reply <some text>", null);
                embed.setDescription("Replies to replied message.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }   
    }
}