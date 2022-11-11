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

        GuineaUtil.recipeDump.dump(ics);

        if (true) return;

        if (args.length == 0) {
            ics.addChatMessage(new ChatComponentText("add arguments lol"));
            return;
        }

        ArrayList<String> opt = new ArrayList<>(Arrays.asList(args));
        ics.addChatMessage(new ChatComponentText("acknowledged"));




        GuineaUtil.info(String.join(", ", args));
    }
}
