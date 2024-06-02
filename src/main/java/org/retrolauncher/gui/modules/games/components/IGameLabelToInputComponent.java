package org.retrolauncher.gui.modules.games.components;

public interface IGameLabelToInputComponent {
    String getInputValue();

    void setLabelText(String value);

    void onClickConfirm(Runnable callback);
}
