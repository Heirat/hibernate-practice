package ru.heirat.app;

import java.util.function.Consumer;

public enum ActionChoice {
    ADD(Application::addAction),
    REMOVE(Application::removeAction),
    SHOW(Application::showAction),
    FIND(Application::findAction),
    EXIT(Application::exitAction);

    final Consumer<Application> action;

    ActionChoice(Consumer<Application> action) {
        this.action = action;
    }
}
