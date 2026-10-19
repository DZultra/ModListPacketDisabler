package net.dzultra.config;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.StateManager;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.string.IStringController;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record ModEntryController(Option<ModEntry> option) implements Controller<ModEntry> {

    @Override
    public Component formatValue() {
        return Component.literal(option.pendingValue().toString());
    }

    @Override
    public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
        List<AbstractWidget> fields = List.of(
                createField(screen, widgetDimension, "ID", ModEntry::id, ModEntry::withId,
                        input -> input.isEmpty() || Identifier.tryParse(input) != null),
                createField(screen, widgetDimension, "Version", ModEntry::version, ModEntry::withVersion,
                        input -> true)
        );
        return new ModEntryWidget(widgetDimension, fields);
    }

    private AbstractWidget createField(
            YACLScreen screen,
            Dimension<Integer> widgetDimension,
            String name,
            Function<ModEntry, String> getter,
            BiFunction<ModEntry, String, ModEntry> setter,
            Predicate<String> validator
    ) {
        Option<String> fieldOption = Option.<String>createBuilder()
                .name(Component.literal(name))
                .stateManager(StateManager.createInstant(
                        "",
                        () -> getter.apply(option.pendingValue()),
                        newVal -> option.requestSet(setter.apply(option.pendingValue(), newVal))
                ))
                .customController(opt -> new FieldController(opt, validator))
                .build();
        return fieldOption.controller().provideWidget(screen, widgetDimension);
    }

    private record FieldController(Option<String> option,
                                   Predicate<String> validator) implements IStringController<String> {
        @Override
        public String getString() {
            return option.pendingValue();
        }

        @Override
        public void setFromString(String value) {
            option.requestSet(value);
        }

        @Override
        public boolean isInputValid(String input) {
            return validator.test(input);
        }
    }
}
