package io.github.guineawheek.guineautil.cmd;

import io.github.guineawheek.guineautil.GuineaUtil;
import io.github.guineawheek.guineautil.dump.RecipeDump;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;

public class GUtilCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "gutil";
    }

    @Override
    public String getCommandUsage(ICommandSender ics) {
        return "/gutil whatever [see docs]";
    }

    @Override
    public void processCommand(ICommandSender ics, String[] args) throws CommandException {
        //ics.addChatMessage(new ChatComponentText("acknowledged"));


        if (args.length == 0 || !args[0].equals("dump")) {
            ics.addChatMessage(new ChatComponentText("usage: /gutil dump"));
            return;
        }
        ics.addChatMessage(new ChatComponentText("starting full recipe dump"));
        GuineaUtil.recipeDump.dump(ics);
        ics.addChatMessage(new ChatComponentText("finished full recipe dump"));
    }
}
