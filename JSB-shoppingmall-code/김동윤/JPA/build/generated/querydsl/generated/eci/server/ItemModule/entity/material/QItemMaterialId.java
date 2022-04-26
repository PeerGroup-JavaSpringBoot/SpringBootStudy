package eci.server.ItemModule.entity.material;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemMaterialId is a Querydsl query type for ItemMaterialId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QItemMaterialId extends BeanPath<ItemMaterialId> {

    private static final long serialVersionUID = 1458047544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemMaterialId itemMaterialId = new QItemMaterialId("itemMaterialId");

    public final eci.server.ItemModule.entity.item.QItem item;

    public final QMaterial material;

    public QItemMaterialId(String variable) {
        this(ItemMaterialId.class, forVariable(variable), INITS);
    }

    public QItemMaterialId(Path<? extends ItemMaterialId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemMaterialId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemMaterialId(PathMetadata metadata, PathInits inits) {
        this(ItemMaterialId.class, metadata, inits);
    }

    public QItemMaterialId(Class<? extends ItemMaterialId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new eci.server.ItemModule.entity.item.QItem(forProperty("item"), inits.get("item")) : null;
        this.material = inits.isInitialized("material") ? new QMaterial(forProperty("material")) : null;
    }

}

