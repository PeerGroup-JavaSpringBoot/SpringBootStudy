package eci.server.ItemModule.entity.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemTypes is a Querydsl query type for ItemTypes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemTypes extends EntityPathBase<ItemTypes> {

    private static final long serialVersionUID = 259537423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemTypes itemTypes = new QItemTypes("itemTypes");

    public final ListPath<ItemTypes, QItemTypes> children = this.<ItemTypes, QItemTypes>createList("children", ItemTypes.class, QItemTypes.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final QItemTypes parent;

    public QItemTypes(String variable) {
        this(ItemTypes.class, forVariable(variable), INITS);
    }

    public QItemTypes(Path<? extends ItemTypes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemTypes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemTypes(PathMetadata metadata, PathInits inits) {
        this(ItemTypes.class, metadata, inits);
    }

    public QItemTypes(Class<? extends ItemTypes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.parent = inits.isInitialized("parent") ? new QItemTypes(forProperty("parent"), inits.get("parent")) : null;
    }

}

