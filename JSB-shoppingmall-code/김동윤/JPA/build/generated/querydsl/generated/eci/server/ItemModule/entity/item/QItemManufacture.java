package eci.server.ItemModule.entity.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemManufacture is a Querydsl query type for ItemManufacture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemManufacture extends EntityPathBase<ItemManufacture> {

    private static final long serialVersionUID = 809979351L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemManufacture itemManufacture = new QItemManufacture("itemManufacture");

    public final QItem item;

    public final QManufacture manufacture;

    public final StringPath partnumber = createString("partnumber");

    public QItemManufacture(String variable) {
        this(ItemManufacture.class, forVariable(variable), INITS);
    }

    public QItemManufacture(Path<? extends ItemManufacture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemManufacture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemManufacture(PathMetadata metadata, PathInits inits) {
        this(ItemManufacture.class, metadata, inits);
    }

    public QItemManufacture(Class<? extends ItemManufacture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.manufacture = inits.isInitialized("manufacture") ? new QManufacture(forProperty("manufacture")) : null;
    }

}

