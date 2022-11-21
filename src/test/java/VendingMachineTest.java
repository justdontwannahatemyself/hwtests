import root.vending.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {
    private static VendingMachine vM = new VendingMachine();
    //  В следующий раз сделаю beforeall метод где буду создавать новый объект.

    @Test
    void checkGettingNumberOfProduct1() {
        vM = new VendingMachine();
        assertEquals(0, vM.getNumberOfProduct1());
    }

    @Test
    void checkGettingNumberOfProduct2() {
        vM = new VendingMachine();
        assertEquals(0, vM.getNumberOfProduct2());
    }

    @Test
    void checkCurrentBalance() {
        vM = new VendingMachine();
        assertEquals(0, vM.getCurrentBalance());
    }

    @Test
    void checkMode() {
        vM = new VendingMachine();
        assertEquals(VendingMachine.Mode.OPERATION, vM.getCurrentMode());
        vM.enterAdminMode(117345294655382L);  // Метод проверен ниже
        assertEquals(VendingMachine.Mode.ADMINISTERING, vM.getCurrentMode());
    }

    @Test
    public void checkAndExitEnteringTestMode() {
        vM = new VendingMachine();

        assertEquals(vM.enterAdminMode(117345294625382L),
                VendingMachine.Response.INVALID_PARAM); // wrong ID
        assertEquals(vM.enterAdminMode(117345294655382L),
                VendingMachine.Response.OK);
        assertEquals(vM.getCurrentMode(), VendingMachine.Mode.ADMINISTERING);
        vM.exitAdminMode();
        assertEquals(vM.getCurrentMode(), VendingMachine.Mode.OPERATION);
        vM.putCoin1();
        assertEquals(vM.enterAdminMode(117345294655382L), VendingMachine.Response.CANNOT_PERFORM);
    }

    @Test
    void checkFillingCoins() {
        vM = new VendingMachine();
        assertEquals(vM.fillCoins(1, 1), VendingMachine.Response.ILLEGAL_OPERATION);
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.fillCoins(-1, 1), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.fillCoins(1, -1), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.fillCoins(22222222, 1), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.fillCoins(1, 2222222), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.fillCoins(1, 2), VendingMachine.Response.OK);
    }

    @Test
    void CheckGettingCurrentSum() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.fillCoins(1, 2);
        assertEquals(vM.getCurrentSum(), 5);
        vM.exitAdminMode();
        assertEquals(vM.getCurrentSum(), 0);
    }

    @Test
    void checkGettingCoins1() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.fillCoins(1, 2);
        assertEquals(1, vM.getCoins1());
        vM.exitAdminMode();
        assertEquals(0, vM.getCoins1());
    }

    @Test
    void checkGettingCoins2() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.fillCoins(1, 2);
        assertEquals(2, vM.getCoins2());
        vM.exitAdminMode();
        assertEquals(0, vM.getCoins2());
    }

    @Test
    void checkSettingNewPrices() {
        vM = new VendingMachine();
        assertEquals(vM.setPrices(1, 1), VendingMachine.Response.ILLEGAL_OPERATION);
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.setPrices(0, 1), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.setPrices(1, 0), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.setPrices(1, 2), VendingMachine.Response.OK);
    }

    @Test
    void checkPrice1() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(1, 2);
        assertEquals(vM.getPrice1(), 1);
    }

    @Test
    void checkPrice2() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(1, 2);
        // Установили в прошлом тесте
        assertEquals(vM.getPrice2(), 2);
    }
    @Test
    void checkFillingFullProducts() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.fillProducts(), VendingMachine.Response.OK);
        vM.exitAdminMode();
        assertEquals(vM.fillProducts(), VendingMachine.Response.ILLEGAL_OPERATION);
    }
    @Test
    void checkPuttingCoin1() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.putCoin1(), VendingMachine.Response.ILLEGAL_OPERATION);
        vM.exitAdminMode();
        assertEquals(vM.putCoin1(), VendingMachine.Response.OK);
        while (VendingMachine.Response.OK == vM.putCoin1()) {
            vM.putCoin1();
        }
        assertEquals(vM.putCoin1(), VendingMachine.Response.CANNOT_PERFORM);
    }
    @Test
    void checkPuttingCoin2() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.putCoin2(), VendingMachine.Response.ILLEGAL_OPERATION);
        vM.exitAdminMode();
        assertEquals(vM.putCoin2(), VendingMachine.Response.OK);
        while (VendingMachine.Response.OK == vM.putCoin2()) {
            vM.putCoin2();
        }
        assertEquals(vM.putCoin2(), VendingMachine.Response.CANNOT_PERFORM);
    }
    @Test
    void checkingReturningMoney() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.returnMoney(), VendingMachine.Response.ILLEGAL_OPERATION);
        vM.exitAdminMode();
        assertEquals(vM.returnMoney(), VendingMachine.Response.OK);
        vM.putCoin2();
        VendingMachine.coinval2 = 1;
        assertEquals(vM.returnMoney(), VendingMachine.Response.TOO_BIG_CHANGE);
        VendingMachine.coinval2 = 2;
        vM.putCoin1();
        assertEquals(vM.returnMoney(), VendingMachine.Response.OK);
        vM.putCoin2();
        assertEquals(vM.returnMoney(), VendingMachine.Response.OK);
    }
    //  Слишком большой метод, разделим его на два.
    @Test
    void returningMoney() {
        vM = new VendingMachine();
        VendingMachine.coinval2 = 3;
        vM.putCoin2();
        VendingMachine.coinval2 = 0;
        vM.putCoin2();
        VendingMachine.coinval2 = 2;
        assertEquals(vM.returnMoney(),  VendingMachine.Response.UNSUITABLE_CHANGE);
    }
    @Test
    void returnMoneyFirstAndSecondType() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.fillCoins(1,1);
        vM.exitAdminMode();
        vM.putCoin1();
        assertEquals(vM.returnMoney(), VendingMachine.Response.OK);
    }
    @Test
    void checkGivingProduct1Exceptions() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.giveProduct1(1), VendingMachine.Response.ILLEGAL_OPERATION);
        vM.exitAdminMode();
        assertEquals(vM.giveProduct1(0), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.giveProduct1(Integer.MAX_VALUE), VendingMachine.Response.INVALID_PARAM);
        // num 1 =1
        assertEquals(vM.giveProduct1(2), VendingMachine.Response.INSUFFICIENT_PRODUCT);
    }
    @Test
    void givingProductNoMoneyOrTooBigChange() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.fillProducts();
        vM.setPrices(2,5);
        vM.exitAdminMode();
        vM.putCoin1();
        assertEquals(vM.giveProduct1(1), VendingMachine.Response.INSUFFICIENT_MONEY);
        VendingMachine.coinval2 = 15;
        vM.putCoin2();
        VendingMachine.coinval2 = 2;
        assertEquals(vM.giveProduct1(1), VendingMachine.Response.TOO_BIG_CHANGE);
    }
    @Test
    void givingProduct1OddEvenReturn() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(2,1);
        vM.fillProducts();
        vM.exitAdminMode();
        vM.putCoin1();
        vM.putCoin1();
        vM.putCoin1();
        assertEquals(vM.giveProduct1(1), VendingMachine.Response.OK);
        vM.putCoin2();
        assertEquals(vM.giveProduct1(1), VendingMachine.Response.OK);
    }
    @Test
    void givingProduct1LackOfMoney() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(1,1);
        vM.fillProducts();
        vM.exitAdminMode();
        vM.putCoin2();
        assertEquals(vM.giveProduct1(1), VendingMachine.Response.UNSUITABLE_CHANGE);
    }
    @Test
    void ridOfAllMoneyNotOnlySecondProduct1() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(1,2);
        vM.fillCoins(1,2);
        vM.fillProducts();
        vM.exitAdminMode();
        vM.putCoin2();
        assertEquals(vM.giveProduct1(1), VendingMachine.Response.OK);

    }
    @Test
    void checkGivingProduct2Exceptions() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        assertEquals(vM.giveProduct2(1), VendingMachine.Response.ILLEGAL_OPERATION);
        vM.exitAdminMode();
        assertEquals(vM.giveProduct2(0), VendingMachine.Response.INVALID_PARAM);
        assertEquals(vM.giveProduct2(Integer.MAX_VALUE), VendingMachine.Response.INVALID_PARAM);
        // num 1 =1
        assertEquals(vM.giveProduct2(2), VendingMachine.Response.INSUFFICIENT_PRODUCT);
    }
    @Test
    void givingProduct2NoMoneyOrTooBigChange() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.fillProducts();
        vM.setPrices(5,2);
        vM.exitAdminMode();
        vM.putCoin1();
        assertEquals(vM.giveProduct2(1), VendingMachine.Response.INSUFFICIENT_MONEY);
        VendingMachine.coinval2 = 15;
        vM.putCoin2();
        VendingMachine.coinval2 = 2;
        assertEquals(vM.giveProduct2(1), VendingMachine.Response.TOO_BIG_CHANGE);
    }
    @Test
    void givingProduct2OddEvenReturn() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(1,2);
        vM.fillProducts();
        vM.exitAdminMode();
        vM.putCoin1();
        vM.putCoin1();
        vM.putCoin1();
        assertEquals(vM.giveProduct2(1), VendingMachine.Response.OK);
        vM.putCoin2();
        assertEquals(vM.giveProduct2(1), VendingMachine.Response.OK);
    }
    @Test
    void givingProduct2LackOfMoney() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(1,1);
        vM.fillProducts();
        vM.exitAdminMode();
        vM.putCoin2();
        assertEquals(vM.giveProduct2(1), VendingMachine.Response.UNSUITABLE_CHANGE);
    }
    @Test
    void ridOfAllMoneyNotOnlySecondProduct2() {
        vM = new VendingMachine();
        vM.enterAdminMode(117345294655382L);
        vM.setPrices(2,1);
        vM.fillCoins(1,2);
        vM.fillProducts();
        vM.exitAdminMode();
        vM.putCoin2();
        assertEquals(vM.giveProduct2(1), VendingMachine.Response.OK);

    }
}
