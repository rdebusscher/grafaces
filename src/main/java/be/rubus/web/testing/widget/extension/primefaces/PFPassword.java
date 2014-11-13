package be.rubus.web.testing.widget.extension.primefaces;

import be.rubus.web.testing.detector.Detector;
import be.rubus.web.testing.widget.extension.primefaces.internal.PasswordFeedbackPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 */
public class PFPassword extends PFInputText {

    private PasswordFeedbackPanel feedbackPanel;

    @PostConstruct
    public void init() {
        List<WebElement> elements = driver.findElements(By.id(getId(root) + "_panel"));
        if (!elements.isEmpty()) {
            feedbackPanel = new PasswordFeedbackPanel();
            grafacesContext.initializePageFragment(feedbackPanel, elements.get(0), this);
        }
    }

    @Override
    public String getType() {
        return "password";
    }

    @Override
    public Detector getDetector() {
        return super.getDetector();
    }

    public boolean hasFeedback() {
        return feedbackPanel != null;
    }

    public boolean isPanelDisplayed() {
        return feedbackPanel.isVisible();
    }

    public void blur() {
        blur(root);
    }

    public String getPanelText() {
        return feedbackPanel.geText();
    }

    public FeedbackColor getPanelColor() {
        return feedbackPanel.getPanelColor();
    }

    public static enum FeedbackColor {
        NONE("0px"), RED("-10px"), ORANGE("-20px"), GREEN("-30px");

        private String position;

        FeedbackColor(String somePosition) {
            position = somePosition;
        }

        public static FeedbackColor getInstance(String currentPosition) {
            FeedbackColor result = null;
            for (FeedbackColor item : FeedbackColor.values()) {
                if (item.position.equals(currentPosition)) {
                    result = item;
                }
            }
            return result;
        }
    }
}
