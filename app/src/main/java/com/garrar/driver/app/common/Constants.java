package com.garrar.driver.app.common;

public interface Constants {

    interface RIDE_REQUEST {
        String DEST_ADD = "d_address";
        String DEST_LAT = "d_latitude";
        String DEST_LONG = "d_longitude";
        String SRC_ADD = "s_address";
        String SRC_LAT = "s_latitude";
        String SRC_LONG = "s_longitude";
        String DEST_ADD1 = "p_address_1";
        String DEST_LAT1 = "p_latitude_1";
        String DEST_LONG1 = "p_longitude_1";
        String DEST_ADD2 = "p_address_2";
        String DEST_LAT2 = "p_latitude_2";
        String DEST_LONG2 = "p_longitude_2";
        String PAYMENT_MODE = "payment_mode";
        String REQUESSTID= "requestid";
        String PAYMENT_MODE_SELECTED = "payment_mode_choose";
        String CARD_ID = "card_id";
        String CARD_LAST_FOUR = "card_last_four";
        String LAST_FOUR = "last_four";
        String DISTANCE_VAL = "distance";
        String SERVICE_TYPE = "service_type";
    }

    interface BroadcastReceiver {
        String INTENT_FILTER = "INTENT_FILTER";
    }

    interface Language {
        String ENGLISH = "en";
        String ARABIC = "ar";
    }

    interface MeasurementType {
        String KM = "Kms";
        String MILES = "miles";
    }

    interface Status {
        String EMPTY = "EMPTY";
        String SERVICE = "SERVICE";
        String SEARCHING = "SEARCHING";
        String STARTED = "STARTED";
        String ARRIVED = "ARRIVED";
        String PICKED_UP = "PICKEDUP";
        String DROPPED = "DROPPED";
        String COMPLETED = "COMPLETED";
        String RATING = "RATING";
        String SELECT_PAYMENT = "SELECT_PAYMENT";
    }

    interface InvoiceFare {
        String MINUTE = "MIN";
        String HOUR = "HOUR";
        String DISTANCE = "DISTANCE";
        String DISTANCE_MIN = "DISTANCEMIN";
        String DISTANCE_HOUR = "DISTANCEHOUR";
    }

    interface PaymentMode {
        String CASH = "CASH";
        String CARD = "CARD";
        String PAYPAL = "PAYPAL";
        String WALLET = "WALLET";
        String BRAINTREE = "BRAINTREE";
        String PAYUMONEY = "PAYUMONEY";
        String PAYTM = "PAYTM";

    }

    interface LocationActions {
        String SELECT_SOURCE = "select_source";
        String SELECT_DESTINATION = "select_destination";
        String CHANGE_DESTINATION = "change_destination";
        String SELECT_HOME = "select_home";
        String SELECT_WORK = "select_work";
    }
}
