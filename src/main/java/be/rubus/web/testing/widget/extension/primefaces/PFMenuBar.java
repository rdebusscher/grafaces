package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.annotation.WidgetValidation;
import be.rubus.web.testing.widget.extension.primefaces.internal.SubMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;

/**
 *
 */
public class PFMenuBar extends PFAbstractMenu {


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

}
