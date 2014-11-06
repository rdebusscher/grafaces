package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.InitializingWidget;
import org.jboss.arquillian.graphene.enricher.WebElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PFMessages extends AbstractPrimeFacesWidget implements InitializingWidget {

    private List<String> errorMessages;
    private List<String> infoMessages;

    @FindBy(className = "ui-messages")
    private WebElement messages;

    private WebElement element;

    @PostConstruct
    public void init() {
        element = WebElementUtils.findElementLazily(By.className("ui-messages"), driver);
    }

    public boolean hasMessages() {
        checkMessages();
        return !errorMessages.isEmpty() || !infoMessages.isEmpty();
    }

    public int getErrorCount() {
        checkMessages();
        return errorMessages.size();
    }

    public String getErrorMessage(int idx) {
        checkMessages();
        return errorMessages.get(idx);
    }

    private void checkMessages() {

        errorMessages = findMessageOfType("ui-messages-error");
        infoMessages = findMessageOfType("ui-messages-info");

    }

    private List<String> findMessageOfType(String messageClassName) {
        List<String> result = new ArrayList<String>();
        //element = WebElementUtils.findElementLazily(By.className("ui-messages"), driver);

        List<WebElement> messagesDiv = element.findElements(By.className(messageClassName));
        if (!messagesDiv.isEmpty()) {
            List<WebElement> elements = messagesDiv.get(0).findElements(By.tagName("li"));
            for (WebElement element : elements) {
                result.add(element.getText());
            }
        }
        return result;

    }
}
