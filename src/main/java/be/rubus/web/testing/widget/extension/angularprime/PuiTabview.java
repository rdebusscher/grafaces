package be.rubus.web.testing.widget.extension.angularprime;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.exception.InconsistentStateException;
import be.rubus.web.testing.widget.extension.angularprime.internal.TabviewNavigations;
import be.rubus.web.testing.widget.extension.angularprime.internal.TabviewPanels;
import org.openqa.selenium.support.FindBy;


/**
 *
 */
public class PuiTabview extends AbstractAngularPrimeWidget {

    @FindBy(className = "pui-tabview-panels")
    private TabviewPanels tabviewPanels;

    @FindBy(className = "pui-tabview-nav")
    private TabviewNavigations tabviewNavigations;


    @WidgetValidation
    public boolean isValidWidget() {
        return containsClassName(root, "ui-widget") && containsClassName(root, "pui-tabview");
    }

    public int getTabCount() {
        int panelCount = tabviewPanels.getCount();
        int tabHeaderCount = tabviewNavigations.getCount();
        if (panelCount != tabHeaderCount) {
            throw new InconsistentStateException("Number of panels (" + panelCount + ") is not the same as number of tab headers (" + tabHeaderCount + ")");
        }
        return panelCount;
    }

    public String getLinkText(int idx) {
        return tabviewNavigations.getHeaderText(idx);
    }

    public void clickOnTab(int idx) {
        tabviewNavigations.clickOnTab(idx);
    }

    public int getSelectedTab() {
        return tabviewNavigations.getSelectedTab();
    }

    public int getVisiblePanel() {
        return tabviewPanels.getVisiblePanel();
    }

    public boolean hasTabCloseIcon(int idx) {
        return tabviewNavigations.hasTabCloseIcon(idx);
    }

    public void closeTab(int idx) {
        tabviewNavigations.closeTab(idx);
        reinitialise();
    }

    public void reinitialise() {
        tabviewNavigations.initTabs();
        tabviewPanels.initPanels();
    }

    public boolean isLeftOrientation() {
        return tabviewNavigations.isLeftOrientation();
    }

    public String getHtmlPanelContent(int idx) {
        return tabviewPanels.getHtmlContent(idx);
    }

    public String getPanelContent(int idx) {

        return tabviewPanels.getText(idx);
    }
}
