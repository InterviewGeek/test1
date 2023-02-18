package com.gofundme.recurringdonations.entities;

/**
 * @author kiran This is an abstract base class for all the entities used by
 *         this application Entities can be viewed as something that represent a
 *         real-world object; ex: a Campaign, Donor etc In production, usually
 *         entities are stored in a DB and usually an entity to schema will have
 *         1-1 mapping In future we can add generic functions like
 *         validateEntity, loadEntity, saveEntity etc functions in this class.
 *         This class can also be used to implement strong contract with child
 *         classes; ex: declare an abstract function to map fields to
 *         schema.columnName which forces child to declare this mapping. This
 *         mapping can be used in save and load functions by this class
 */

abstract public class AbstractGfmDonationEntity {
}