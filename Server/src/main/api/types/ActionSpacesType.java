package main.api.types;

import java.io.Serializable;

/**
 * @author Andrea
 * @author Luca
 */
public enum ActionSpacesType implements Serializable{
    SINGLE_PRODUCTION, LARGE_PRODUCTION, SINGLE_HARVEST, LARGE_HARVEST, COUNCIL, MARKET, TOWERS;
}
