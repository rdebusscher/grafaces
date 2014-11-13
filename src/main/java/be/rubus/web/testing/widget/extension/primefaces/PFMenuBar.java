package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.ArrayUtil;
import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.widget.extension.primefaces.internal.SubMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PFMenuBar extends AbstractPrimeFacesWidget {

    private List<SubMenu> subMenus = new ArrayList<SubMenu>();

    @PostConstruct
    public void init() {
        WebElement subMenuList = root.findElement(By.xpath("ul"));

        for (WebElement item : subMenuList.findElements(By.xpath("li"))) {

            SubMenu subMenu = new SubMenu();
            grafacesContext.initializePageFragment(subMenu, item, this);
            SubMenu subMenuProxy = grafacesContext.asProxy(subMenu, driver);
            subMenus.add(subMenuProxy);

        }

    }

    @WidgetValidation
    public boolean isValidWidget() {
        return isHtmlElement(root, "DIV") && containsClassName(root, "ui-menubar");
    }


    public int getCount(Integer... idx) {
        int result;
        if (idx == null || idx.length == 0) {
            result = subMenus.size();
        } else {
            SubMenu subMenu = subMenus.get(idx[0]);
            result = subMenu.getCount(ArrayUtil.dropFirstItem(idx));
        }
        return result;
    }


    public String getLabel(Integer... idx) {
        if (idx == null || idx.length == 0) {
            throw new IllegalArgumentException("idx can't be null or empty");
        }

        SubMenu subMenu = subMenus.get(idx[0]);
        return subMenu.getLabel(ArrayUtil.dropFirstItem(idx));
    }

    public void click(Integer... idx) {
        if (idx == null || idx.length == 0) {
            throw new IllegalArgumentException("idx can't be null or empty");
        }

        SubMenu subMenu = subMenus.get(idx[0]);
        subMenu.click(ArrayUtil.dropFirstItem(idx));
    }

}
