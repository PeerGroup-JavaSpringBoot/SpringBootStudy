package eci.server.ItemModule.entity.material;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemMaterial is a Querydsl query type for ItemMaterial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemMaterial extends EntityPathBase<ItemMaterial> {

    private static final long serialVersionUID = -570549187L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemMaterial itemMaterial = new QItemMaterial("itemMaterial");

    public final eci.server.ItemModule.entitycommon.QEntityDate _super = new eci.server.ItemModule.entitycommon.QEntityDate(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final eci.server.ItemModule.entity.item.QItem item;

    public final QMaterial material;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QItemMaterial(String variable) {
        this(ItemMaterial.class, forVariable(variable), INITS);
    }

    public QItemMaterial(Path<? extends ItemMaterial> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemMaterial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemMaterial(PathMetadata metadata, PathInits inits) {
        this(ItemMaterial.class, metadata, inits);
    }

    public QItemMaterial(Class<? extends ItemMaterial> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new eci.server.ItemModule.entity.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.material = inits.isInitialized("material") ? new QMaterial(forProperty("material")) : null;
    }

}

