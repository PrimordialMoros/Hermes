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

package me.moros.hermes.locale;

import me.moros.hermes.HermesUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.command.ConsoleCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;
import static net.kyori.adventure.text.format.NamedTextColor.*;

/**
 * @see TranslationManager
 */
public interface Message {
  Component PREFIX = text("[", DARK_GRAY)
    .append(text("Hermes", DARK_AQUA))
    .append(text("] ", DARK_GRAY));

  Args0 NO_RECIPIENT = () -> translatable("hermes.command.reply.no-recipient", RED);

  Args1<String> OFFLINE_RECIPIENT = name -> translatable("hermes.command.reply.offline-recipient", YELLOW)
    .args(text(name, HermesUtil.BASE_COLOR));

  Args0 SPY_ON = () -> translatable("hermes.command.spy.on", GREEN);

  Args0 SPY_OFF = () -> translatable("hermes.command.spy.off", YELLOW);

  Args0 RELOAD = () -> brand(translatable("hermes.command.reload", GREEN));

  Args2<String, String> VERSION_COMMAND_HOVER = (author, link) -> translatable("hermes.command.version.hover", DARK_AQUA)
    .args(text(author, GREEN), text(link, GREEN));

  static @NonNull Component brand(@NonNull ComponentLike message) {
    return text().append(PREFIX).append(message).build();
  }

  interface Args0 {
    @NonNull Component build();

    default void send(@NonNull Audience audience) {
      if (audience instanceof ConsoleCommandSender) {
        audience.sendMessage(GlobalTranslator.render(build(), TranslationManager.DEFAULT_LOCALE));
        return;
      }
      audience.sendMessage(build());
    }
  }

  interface Args1<A0> {
    @NonNull Component build(@NonNull A0 arg0);

    default void send(@NonNull Audience audience, @NonNull A0 arg0) {
      if (audience instanceof ConsoleCommandSender) {
        audience.sendMessage(GlobalTranslator.render(build(arg0), TranslationManager.DEFAULT_LOCALE));
        return;
      }
      audience.sendMessage(build(arg0));
    }
  }

  interface Args2<A0, A1> {
    @NonNull Component build(@NonNull A0 arg0, @NonNull A1 arg1);

    default void send(@NonNull Audience audience, @NonNull A0 arg0, @NonNull A1 arg1) {
      if (audience instanceof ConsoleCommandSender) {
        audience.sendMessage(GlobalTranslator.render(build(arg0, arg1), TranslationManager.DEFAULT_LOCALE));
        return;
      }
      audience.sendMessage(build(arg0, arg1));
    }
  }
}
