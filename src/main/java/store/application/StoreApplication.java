package store.application;

import store.controller.InputController;
import store.controller.StoreController;
import store.view.InputView;
import store.view.OutputView;

public class StoreApplication {
    public void run() {
        InputView inputView = new InputView();
        InputController inputController = new InputController(inputView);
        OutputView outputView = new OutputView();
        StoreController storeController = new StoreController(inputController, outputView);

        storeController.run();
    }
}
