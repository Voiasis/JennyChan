package com.voiasis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class BotStartup {
    public static void main(String[] args) throws LoginException, IOException {
        String token = Files.readString(Path.of("E:\\Dev\\JennyChan\\.env"));

        JDABuilder.createDefault(token)
        .setStatus(OnlineStatus.ONLINE)
        .setActivity(Activity.playing("Minecraft"))
        .addEventListeners(new BotCommands(), new PlayerController())
        .setChunkingFilter(ChunkingFilter.ALL)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
        .build();
    }
}