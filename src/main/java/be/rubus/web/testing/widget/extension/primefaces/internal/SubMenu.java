package be.rubus.web.testing.widget.extension.primefaces.internal;

import be.rubus.web.testing.ArrayUtil;
import be.rubus.web.testing.widget.extension.primefaces.AbstractPrimeFacesWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SubMenu extends AbstractPrimeFacesWidget {


    private List<SubMenu> menuItems = new ArrayList<SubMenu>();

    private WebElement itemLabel;
    private boolean hasText;

    @PostConstruct
    public void init() {

        itemLabel = root.findElement(By.xpath("a"));
        hasText = !itemLabel.findElements(By.className("ui-menuitem-text")).isEmpty();

        // Need to use List version, when not found it throws an exception
        List<WebElement> ul = root.findElements(By.xpath("ul"));

        if (!ul.isEmpty()) {
            WebElement subMenuList = ul.get(0);

            for (WebElement item : subMenuList.findElements(By.xpath("li"))) {

                SubMenu subMenu = new SubMenu();
                grafacesContext.initializePageFragment(subMenu, item, this);
                SubMenu subMenuProxy = grafacesContext.asProxy(subMenu, driver);
                menuItems.add(subMenuProxy);

            }
        }
    }

    public int getCount(Integer[] idx) {
        int result;
        if (idx == null || idx.length == 0) {
            result = menuItems.size();
        } else {
            SubMenu subMenu = menuItems.get(idx[0]);
            result = subMenu.getCount(ArrayUtil.dropFirstItem(idx));
        }
        return result;
    }

    public String getLabel(Integer[] idx) {
        String result;
        if (idx == null || idx.length == 0) {
            result = hasText ? getContentInAnyCase(itemLabel) : "";
        } else {
            SubMenu subMenu = menuItems.get(idx[0]);
            result = subMenu.getLabel(ArrayUtil.dropFirstItem(idx));
        }
        return result;
    }

    public void click(Integer[] idx) {
        itemLabel.click();
        if (idx != null && idx.length > 0) {
            SubMenu subMenu = menuItems.get(idx[0]);
            subMenu.click(ArrayUtil.dropFirstItem(idx));
        }

    }
}
