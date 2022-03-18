package BotCommands.Moderation;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RemoveroleCommand {
    public String prefix = "!"; //bot prefix
    public String ftr = "Bot created by Voiasis#0001";
    public String avURL = "https://cdn.discordapp.com/avatars/472899069136601099/a_a4016f032af0656365c32677b5efe43e.gif";

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(prefix + "removerole")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setFooter(ftr, avURL);

            if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    Role roleToRemove = event.getMessage().getMentionedRoles().get(0);
                    event.getGuild().removeRoleFromMember(member, roleToRemove).queue();

                    event.getMessage().reply("Removed the role " + roleToRemove.getAsMention() + " from " + member.getAsMention() + '.').queue();
                } else {
                    embed.setTitle(prefix + "removerole <@role> <@user>", null);
                    embed.setDescription("Removes a role from a user.");
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                embed.setTitle(prefix + "removerole <@role> <@user>", null);
                embed.setDescription("Removes a role from a user.");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }   
    }
} //TODO check for roles permission of command user