import java.awt.*;
import java.util.Random;

public class AItank extends Tank {
    private int stepCount = 0;
    private int stepNumber = 0;
    public AItank(int x, int y) {
        super(x, y);
        Random random=new Random();
        stepNumber = 10+random.nextInt(20);
    }
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }
    @Override
    public void caculateData() {
        stepCount++;
        if(stepCount>=stepNumber){
            Random random = new Random();
            setDirection(random.nextInt(4));
            stepCount = 0;
        }
        super.caculateData();

    }
    @Override
    public Bullet fire() {
        Random random = new Random();
        if (random.nextInt(30) == 0) {
            return super.fire();
        }
        return null;
    }
}
