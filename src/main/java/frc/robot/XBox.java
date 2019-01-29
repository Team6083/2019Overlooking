package frc.robot;

import org.team6083.lib.util.XBoxController;

public class XBox extends XBoxController{
    public XBox(int port){
        super(port);
    }

    @Override
    public boolean toggleReverseButton(){
        return this.getBackButton();
    }
}