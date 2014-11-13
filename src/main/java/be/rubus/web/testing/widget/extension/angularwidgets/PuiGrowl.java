package be.rubus.web.testing.widget.extension.angularwidgets;

import be.rubus.web.testing.GrafacesContext;
import be.rubus.web.testing.annotation.Grafaces;
import be.rubus.web.testing.widget.extension.angularwidgets.internal.GrowlMessage;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PuiGrowl {

    @Drone
    protected WebDriver driver;

    @Grafaces
    private GrafacesContext grafacesContext;


    private List<GrowlMessage> getAllMessages() {
        List<WebElement> elements = driver.findElements(By.className("pui-growl-item-container"));
        List<GrowlMessage> result = new ArrayList<GrowlMessage>();
        for (WebElement element : elements) {
            GrowlMessage message = new GrowlMessage();
            grafacesContext.initializePageFragment(message, element, this);
            result.add(message);
        }
        return result;
    }

    public int getNumberOfMessages() {
        return getAllMessages().size();
    }

    public String getMessageTitle(int idx) {
        return getAllMessages().get(idx).getTitle();
    }

    public String getMessageText(int idx) {
        return getAllMessages().get(idx).getMessage();
    }

    public Severity getMessageSeverity(int idx) {
        return getAllMessages().get(idx).getSeverity();
    }

    public void closeMessage(int idx) {
        getAllMessages().get(idx).close();
    }

    public static enum Severity {
        INFO, WARN, ERROR, FATAL

    }
}
