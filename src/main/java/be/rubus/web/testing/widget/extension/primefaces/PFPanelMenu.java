package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.widget.extension.primefaces.internal.SubMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class PFPanelMenu extends PFAbstractMenu {

    @WidgetValidation
    public boolean isValidWidget() {
        return isHtmlElement(root, "DIV") && containsClassName(root, "ui-panelmenu");
    }

    @PostConstruct
    public void init() {
        List<WebElement> subMenuList = root.findElements(By.className("ui-panelmenu-panel"));
        for (WebElement item : subMenuList) {

            SubMenu subMenu = new SubMenu();
            grafacesContext.initializePageFragment(subMenu, item, this);
            SubMenu subMenuProxy = grafacesContext.asProxy(subMenu, driver);
            subMenus.add(subMenuProxy);

        }
    }


}
