package eci.server.ItemModule.entity.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1646621834L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final eci.server.ItemModule.entity.entitycommon.QEntityDate _super = new eci.server.ItemModule.entity.entitycommon.QEntityDate(this);

    public final ListPath<Attachment, QAttachment> attachments = this.<Attachment, QAttachment>createList("attachments", Attachment.class, QAttachment.class, PathInits.DIRECT2);

    public final QColor color;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath height = createString("height");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> itemNumber = createNumber("itemNumber", Integer.class);

    public final ListPath<ItemManufacture, QItemManufacture> manufactures = this.<ItemManufacture, QItemManufacture>createList("manufactures", ItemManufacture.class, QItemManufacture.class, PathInits.DIRECT2);

    public final ListPath<eci.server.ItemModule.entity.material.ItemMaterial, eci.server.ItemModule.entity.material.QItemMaterial> materials = this.<eci.server.ItemModule.entity.material.ItemMaterial, eci.server.ItemModule.entity.material.QItemMaterial>createList("materials", eci.server.ItemModule.entity.material.ItemMaterial.class, eci.server.ItemModule.entity.material.QItemMaterial.class, PathInits.DIRECT2);

    public final eci.server.ItemModule.entity.member.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final BooleanPath revise_progress = createBoolean("revise_progress");

    public final BooleanPath tempsave = createBoolean("tempsave");

    public final ListPath<Image, QImage> thumbnail = this.<Image, QImage>createList("thumbnail", Image.class, QImage.class, PathInits.DIRECT2);

    public final StringPath type = createString("type");

    public final StringPath weight = createString("weight");

    public final StringPath width = createString("width");

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.color = inits.isInitialized("color") ? new QColor(forProperty("color")) : null;
        this.member = inits.isInitialized("member") ? new eci.server.ItemModule.entity.member.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

