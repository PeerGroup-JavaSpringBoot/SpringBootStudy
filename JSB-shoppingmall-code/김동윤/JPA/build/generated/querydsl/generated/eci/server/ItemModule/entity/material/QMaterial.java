package eci.server.ItemModule.entity.material;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMaterial is a Querydsl query type for Material
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMaterial extends EntityPathBase<Material> {

    private static final long serialVersionUID = -1594365558L;

    public static final QMaterial material = new QMaterial("material");

    public final eci.server.ItemModule.entitycommon.QEntityDate _super = new eci.server.ItemModule.entitycommon.QEntityDate(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QMaterial(String variable) {
        super(Material.class, forVariable(variable));
    }

    public QMaterial(Path<? extends Material> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaterial(PathMetadata metadata) {
        super(Material.class, metadata);
    }

}

