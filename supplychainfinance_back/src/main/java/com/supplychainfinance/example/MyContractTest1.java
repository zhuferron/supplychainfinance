package com.supplychainfinance.example;

//import org.hyperledger.fabric.contract.Context;
//import org.hyperledger.fabric.shim.ChaincodeException;
//import org.hyperledger.fabric.shim.ChaincodeStub;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.ThrowableAssert.catchThrowable;
//
//import static org.mockito.Mockito.inOrder;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import org.mockito.InOrder;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//public class MyContractTest {
//    @Nested
//    class InvokeInitTransaction {
//        @Test
//        public void init() {
//            MyContract contract = new MyContract();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//
//            contract.init(ctx);
//
//            InOrder inOrder = inOrder(stub);
//            inOrder.verify(stub).putStringState("Name", "Fabric@Java");
//        }
//    }
//
//    @Nested
//    class InvokeQueryTransaction {
//        @Test
//        public void hi() {
//            MyContract contract = new MyContract();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            when(ctx.getStub()).thenReturn(stub);
//            when(stub.getStringState("Name")).thenReturn("Fabric@Java");
//            assertThat(contract.hi(ctx).equals("Fabric@Java"));
//        }
//    }
//
//    @Nested
//    class InvokeSetValueTransaction {
//		 @Test
//         public void SetValue() {
//		     MyContract contract = new MyContract();
//		     Context ctx = mock(Context.class);
//		     ChaincodeStub stub = mock(ChaincodeStub.class);
//		     String key = "truth";
//		     String value = "ferron is a good boy";
//
//		     when(ctx.getStub()).thenReturn(stub);
//
//		     contract.SetValue(ctx,key,value);
//		     InOrder inOrder = inOrder(stub);
//		     inOrder.verify(stub).putStringState(key, value);
//		 }
//    }
//
//    @Nested
//    class InvokeGetValueTransaction {
//        @Test
//        public void GetValue() {
//            MyContract contract = new MyContract();
//            Context ctx = mock(Context.class);
//            ChaincodeStub stub = mock(ChaincodeStub.class);
//            String key = "truth";
//            when(ctx.getStub()).thenReturn(stub);
//
//            when(stub.getStringState(key)).thenReturn("ferron is a good boy");
//            assertThat(contract.GetValue(ctx,key).equals("ferron is a good boy"));
//        }
//    }
//}
