package BotCommands.Utilities;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ServerinfoCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        

        if (args[0].equalsIgnoreCase(prefix + "serverinfo")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

            String members = Integer.toString(event.getGuild().getMemberCount());
            String month = Integer.toString(event.getGuild().getTimeCreated().getMonthValue());
            String day = Integer.toString(event.getGuild().getTimeCreated().getDayOfMonth());
            String year = Integer.toString(event.getGuild().getTimeCreated().getYear());
            String creation = month + "/" + day + "/" + year;
            String owner = event.getGuild().getOwner().getAsMention();
            String boosts = Integer.toString(event.getGuild().getBoostCount());

            embed.setTitle(event.getGuild().getName(), null);
            //embed.addField("Description", event.getGuild().getDescription(), false);
            embed.addField("Owner", owner, false);
            embed.addField("Created", creation, false);
            embed.addField("Default Channel", "<#" + event.getGuild().getDefaultChannel().getId() + ">", false);
            //embed.addField("Online Members", event.getGuild().getMemberCount(), false);
            embed.addField("Total Members", members, false);
            //embed.addField("Channels (text)", event.getGuild(), false);
            //embed.addField("Channels (voice)", event.getGuild(), false);
            //embed.addField("Roles", event.getGuild(), false);
            //embed.addField("Emojis", event.getGuild(), false);
            //embed.addField("Voice Region", event.getGuild(), false);
            //embed.addField("Ban Count", event.getGuild(), false);
            embed.addField("Boosts", boosts, false);
            embed.addField("Server ID", event.getGuild().getId(), false);

            embed.addField("Banner", "", false);
            embed.setImage(event.getGuild().getBannerUrl());

            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }  
    }
} //TODO finish this