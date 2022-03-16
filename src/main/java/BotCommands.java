import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotCommands extends ListenerAdapter {
    public String prefix = "!"; //bot prefix
    public String botID = "<@!952761165577060453>"; //bot mention ID

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        //mention reply start
        if (args[0].equalsIgnoreCase(botID)) {
            event.getMessage().reply("Hey cutie~ ;3" + "\r\n" + "Say \"" + prefix + "commands\" to get started!").queue();
        }
        //mention reply end

        //commands start
        if (args[0].equalsIgnoreCase(prefix + "commands")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setTitle("Commands", null);
            embed.setDescription("List of all bot commands.");
            embed.addField(prefix + "commands", "Shows this message.", false);

            embed.addField(prefix + "uptime", "Shows the bots current uptime.", false);
            embed.addField(prefix + "say <some text>", "Sends inputted text.", false);
            embed.addField(prefix + "message <some text>", "Sends you a message.", false);
            embed.addField(prefix + "giverole <@role> <@user>", "Gives a user a role.", false);
            embed.addField(prefix + "removerole <@role> <@user>", "Removes a role from a user.", false);
            embed.addField(prefix + "kick <@member>", "Kicks a member.", false);
            embed.addField(prefix + "shutdown", "Shuts down bot.", false);

            embed.setFooter("Bot created by Voiasis#0001", null);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //commands end

        //uptime start
        if (args[0].equalsIgnoreCase(prefix + "uptime")) {
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            long ut = rb.getUptime();
            long millis = ut % 1000;
            long second = (ut / 1000) % 60;
            long minute = (ut / (1000 * 60)) % 60;
            long hour = (ut / (1000 * 60 * 60)) % 24;
            String uptime = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.MAGENTA);
            embed.setTitle("JennyChan's Uptime", null);
            embed.setDescription("" + uptime);
            embed.setFooter("Bot created by Voiasis#0001", null);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }
        //uptime end

        //say start TODO fix error when no text is given. add embed
        if (args[0].equalsIgnoreCase(prefix + "say")) {
            event.getChannel().sendMessage(event.getMessage().getContentRaw().substring(5)).queue();
            event.getMessage().delete().queue();
        }
        //say end

        //message start TODO fix error when no text is given. add embed
        if (args[0].equalsIgnoreCase(prefix + "message")) {
            String a = event.getMessage().getContentRaw().substring(9);
            event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("" + a)).queue();
        }
        //message end

        //giverole start
        if (args[0].equalsIgnoreCase(prefix + "giverole")) {
            if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    Role roleToGive = event.getMessage().getMentionedRoles().get(0);
                    event.getGuild().addRoleToMember(member, roleToGive).queue();

                    event.getMessage().reply("Gave the role " + roleToGive.getAsMention() + " to " + member.getAsMention() + ".").queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle(prefix + "giverole <@role> <@user>", null);
                    embed.setDescription("Gives a user a role.");
                    embed.setFooter("Bot created by Voiasis#0001", null);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "giverole <@role> <@user>", null);
                embed.setDescription("Gives a user a role.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //giverole end

        //removerole start
        if (args[0].equalsIgnoreCase(prefix + "removerole")) {
            if (event.getMessage().getMentionedRoles().toArray().length == 1) {
                if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                    Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                    Role roleToRemove = event.getMessage().getMentionedRoles().get(0);
                    event.getGuild().removeRoleFromMember(member, roleToRemove).queue();

                    event.getMessage().reply("Removed the role " + roleToRemove.getAsMention() + " from " + member.getAsMention() + '.').queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.MAGENTA);
                    embed.setTitle(prefix + "removerole <@role> <@user>", null);
                    embed.setDescription("Removes a role from a user.");
                    embed.setFooter("Bot created by Voiasis#0001", null);
                    event.getChannel().sendMessageEmbeds(embed.build()).queue();
                    embed.clear();
                }
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "removerole <@role> <@user>", null);
                embed.setDescription("Removes a role from a user.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //removerole end

        //kick start
        if (args[0].equalsIgnoreCase(prefix + "kick")) {
            if (event.getMessage().getMentionedUsers().toArray().length == 1) {
                Member member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
                event.getGuild().kick(member, null).queue();

                event.getMessage().reply(member.getAsMention() + " has been kicked.").queue();
            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.MAGENTA);
                embed.setTitle(prefix + "kick <@member>", null);
                embed.setDescription("Kicks a member.");
                embed.setFooter("Bot created by Voiasis#0001", null);
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                embed.clear();
            }
        }
        //kick end

        //shutdown start
        if (args[0].equalsIgnoreCase(prefix + "shutdown")) {
            event.getMessage().reply("Shutting down.").queue();
            System.err.println("Shutting down.");
            System.exit(1);
        }
        //shutdown end
    }
}