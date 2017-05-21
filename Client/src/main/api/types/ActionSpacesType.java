package main.api.types;

import java.io.Serializable;

/**
 * @author lampa
 */
public enum ActionSpacesType implements Serializable{
    SINGLE_PRODUCTION, LARGE_PRODUCTION, SINGLE_HARVEST, LARGE_HARVEST, COUNCIL, MARKET, TOWERS;
}
