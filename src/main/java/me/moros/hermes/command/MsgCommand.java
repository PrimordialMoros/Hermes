/*
 * Copyright 2021-2022 Moros
 *
 * This file is part of Hermes.
 *
 * Hermes is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hermes is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Hermes. If not, see <https://www.gnu.org/licenses/>.
 */

package me.moros.hermes.command;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.meta.CommandMeta;
import me.moros.hermes.Hermes;
import me.moros.hermes.User;
import me.moros.hermes.registry.Registries;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class MsgCommand {
  private final CommandManager manager;

  MsgCommand(@NonNull CommandManager manager) {
    this.manager = manager;
    construct();
  }

  private void construct() {
    manager.command(manager.commandBuilder("msg")
      .meta(CommandMeta.DESCRIPTION, "Sends a private message to the specified player")
      .permission("hermes.command.msg")
      .argument(PlayerArgument.of("player"))
      .argument(StringArgument.greedy("msg"))
      .senderType(Player.class)
      .handler(c -> onMsg(c.getSender(), c.get("player"), c.getOrDefault("msg", "")))
    );
  }

  private void onMsg(CommandSender commandSender, Player player, String msg) {
    if (player != null) {
      User sender = Registries.USERS.user((Player) commandSender);
      User receiver = Registries.USERS.user(player);
      Hermes.herald().handleMessage(sender, receiver, msg);
    }
  }
}
