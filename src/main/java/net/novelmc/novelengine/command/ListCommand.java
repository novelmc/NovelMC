package net.novelmc.novelengine.command;

import net.novelmc.novelengine.rank.architect.ArchitectList;
import net.novelmc.novelengine.command.util.CommandParameters;
import net.novelmc.novelengine.command.util.SourceType;
import net.novelmc.novelengine.rank.Rank;
import net.novelmc.novelengine.rank.Title;
import net.novelmc.novelengine.rank.staff.StaffList;
import net.novelmc.novelengine.util.NUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandParameters(description = "A list of online players", source = SourceType.BOTH, rank = Rank.IMPOSTOR)
public class ListCommand
{

    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
    {
        if (args.length > 0)
        {
            return false;
        }

        java.util.List<String> impostors = new ArrayList<>();
        java.util.List<String> non_ops = new ArrayList<>();
        java.util.List<String> ops = new ArrayList<>();
        java.util.List<String> trainees = new ArrayList<>();
        java.util.List<String> mods = new ArrayList<>();
        java.util.List<String> senior_mods = new ArrayList<>();
        java.util.List<String> admins = new ArrayList<>();
        java.util.List<String> architects = new ArrayList<>();
        java.util.List<String> developers = new ArrayList<>();
        java.util.List<String> managers = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        sb.append(ChatColor.GRAY).append("There are ")
                .append(ChatColor.DARK_GRAY).append(Bukkit.getOnlinePlayers().size()).append("/").append(Bukkit.getMaxPlayers())
                .append(ChatColor.GRAY).append(" players online")
                .append(ChatColor.DARK_GRAY).append(":\n");

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (StaffList.isImpostor(player) || ArchitectList.isImpostor(player))
            {
                impostors.add(ChatColor.GRAY + player.getName());
            }
            else if (Rank.getRank(player).isAtLeast(Rank.MANAGER))
            {
                managers.add(ChatColor.GRAY + player.getName());
            }
            else if (NUtil.DEVELOPERS.contains(player.getName()))
            {
                developers.add(ChatColor.GRAY + player.getName());
            }
            else if (ArchitectList.isArchitect(player))
            {
                architects.add(ChatColor.GRAY + player.getName());
            }
            else if (Rank.getRank(player).isAtLeast(Rank.ADMIN))
            {
                admins.add(ChatColor.GRAY + player.getName());
            }
            else if (Rank.getRank(player).isAtLeast(Rank.SENIOR_MOD))
            {
                senior_mods.add(ChatColor.GRAY + player.getName());
            }
            else if (Rank.getRank(player).isAtLeast(Rank.MOD))
            {
                mods.add(ChatColor.GRAY + player.getName());
            }
            else if (Rank.getRank(player).isAtLeast(Rank.TRAINEE))
            {
                trainees.add(ChatColor.GRAY + player.getName());
            }
            else if (player.isOp())
            {
                ops.add(ChatColor.GRAY + player.getName());
            }
            else
            {
                non_ops.add(ChatColor.GRAY + player.getName());
            }
        }

        sb.append((impostors.isEmpty() ? "" : Rank.IMPOSTOR.getTag() + ": " + StringUtils.join(impostors, ", ") + "\n"))
                .append((non_ops.isEmpty() ? "" : Rank.NON_OP.getTag() + ": " + StringUtils.join(non_ops, ", ") + "\n"))
                .append((ops.isEmpty() ? "" : Rank.OP.getTag() + ": " + StringUtils.join(ops, ", ") + "\n"))
                .append((trainees.isEmpty() ? "" : Rank.TRAINEE.getTag() + ": " + StringUtils.join(trainees, ", ") + "\n"))
                .append((mods.isEmpty() ? "" : Rank.MOD.getTag() + ": " + StringUtils.join(mods, ", ") + "\n"))
                .append((senior_mods.isEmpty() ? "" : Rank.SENIOR_MOD.getTag() + StringUtils.join(senior_mods, ", ") + "\n"))
                .append((admins.isEmpty() ? "" : Rank.ADMIN.getTag() + ": " + StringUtils.join(admins, ", ") + "\n"))
                .append((architects.isEmpty() ? "" : Title.ARCHITECT.getTag() + ": " + StringUtils.join(architects, ", ") + "\n"))
                .append((developers.isEmpty() ? "" : Title.DEVELOPER.getTag() + ": " + StringUtils.join(developers, ", ") + "\n"))
                .append((managers.isEmpty() ? "" : Rank.MANAGER.getTag() + ": " + StringUtils.join(managers, ", ")));

        sender.sendMessage(sb.toString());
        return true;
    }
}