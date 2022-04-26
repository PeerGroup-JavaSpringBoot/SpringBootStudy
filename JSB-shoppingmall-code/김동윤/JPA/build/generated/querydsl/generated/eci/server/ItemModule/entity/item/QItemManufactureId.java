package eci.server.ItemModule.entity.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemManufactureId is a Querydsl query type for ItemManufactureId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QItemManufactureId extends BeanPath<ItemManufactureId> {

    private static final long serialVersionUID = 1001078098L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemManufactureId itemManufactureId = new QItemManufactureId("itemManufactureId");

    public final QItem item;

    public final QManufacture manufacture;

    public QItemManufactureId(String variable) {
        this(ItemManufactureId.class, forVariable(variable), INITS);
    }

    public QItemManufactureId(Path<? extends ItemManufactureId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemManufactureId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemManufactureId(PathMetadata metadata, PathInits inits) {
        this(ItemManufactureId.class, metadata, inits);
    }

    public QItemManufactureId(Class<? extends ItemManufactureId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.manufacture = inits.isInitialized("manufacture") ? new QManufacture(forProperty("manufacture")) : null;
    }

}

