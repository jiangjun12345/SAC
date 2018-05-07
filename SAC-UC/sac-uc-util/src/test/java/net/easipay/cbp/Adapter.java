package net.easipay.cbp;

class Adapter extends Adaptee implements Target{  
    public void request() {  
        super.specificRequest();  
    }  
}  
