package be.rubus.web.testing.widget.extension.angularwidgets.internal;

import be.rubus.web.testing.exception.InconsistentStateException;
import be.rubus.web.testing.widget.extension.angularwidgets.AbstractAngularWidgetsWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TabviewNavigations extends AbstractAngularWidgetsWidget {

    private List<TabviewHeader> tabHeaders;

    @PostConstruct
    public void initTabs() {
        tabHeaders = new ArrayList<TabviewHeader>();
        List<WebElement> headers = root.findElements(By.tagName("li"));
        for (WebElement header : headers) {
            TabviewHeader tabHeader = new TabviewHeader();
            grafacesContext.initializePageFragment(tabHeader, header, this);
            tabHeaders.add(tabHeader);
        }
    }

    public int getCount() {
        return tabHeaders.size();
    }

    public String getHeaderText(int idx) {
        return tabHeaders.get(idx).getLinkText();
    }

    public void clickOnTab(int idx) {
        tabHeaders.get(idx).click();
    }

    public int getSelectedTab() {
        int result = -1;
        for (int i = 0; i < tabHeaders.size(); i++) {
            if (tabHeaders.get(i).isSelected()) {
                result = i;
            }
        }
        return result;
    }

    public boolean hasTabCloseIcon(int idx) {
        return tabHeaders.get(idx).hasCloseIcon();
    }

    public void closeTab(int idx) {
        tabHeaders.get(idx).clickOnCloseIcon();
    }

    public boolean isLeftOrientation() {
        if (tabHeaders.size() < 2) {
            throw new InconsistentStateException("At least 2 tab headers required to determine if it is left oriented.");
        }
        Point tab1 = tabHeaders.get(0).getLocation();
        Point tab2 = tabHeaders.get(1).getLocation();
        return tab1.getX() == tab2.getX();
    }
}
