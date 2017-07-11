package testing;

import centralsystem.factory.CSystemFactory;
import database.factories.DBMapperFactory;
import database.factories.SimMapperFactory;

public class TestSystem {
    
    public static void main(String[] args) {
        CSystemFactory.getInstance().buildCSystem(DBMapperFactory.class.getCanonicalName());
    }
    
}