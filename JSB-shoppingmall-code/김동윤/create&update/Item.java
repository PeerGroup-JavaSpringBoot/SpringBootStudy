package eci.server.ItemModule.entity.item;

import eci.server.ItemModule.dto.item.ItemUpdateRequest;

import eci.server.ItemModule.entity.entitycommon.EntityDate;
import eci.server.ItemModule.entity.material.ItemMaterial;
import eci.server.ItemModule.entity.material.ItemMaterialId;
import eci.server.ItemModule.entity.material.Material;
import eci.server.ItemModule.entity.member.Member;
import eci.server.ItemModule.exception.item.ColorNotFoundException;
import eci.server.ItemModule.exception.item.ManufactureNotFoundException;
import eci.server.ItemModule.exception.item.MaterialNotFoundException;
import eci.server.ItemModule.exception.member.sign.MemberNotFoundException;
import eci.server.ItemModule.repository.color.ColorRepository;
import eci.server.ItemModule.repository.item.ItemManufactureRepository;
import eci.server.ItemModule.repository.item.ItemMaterialRepository;
import eci.server.ItemModule.repository.manufacture.ManufactureRepository;
import eci.server.ItemModule.repository.material.MaterialRepository;
import eci.server.ItemModule.repository.member.MemberRepository;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends EntityDate {
    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE2")
   @SequenceGenerator(name="SEQUENCE2", sequenceName="SEQUENCE2", allocationSize=1)

    private Long id;

    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
//    private String type;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer itemNumber;
    //save ??? ?????? type + id ????????? ??????

    @Column(nullable = false)
    private String width;

    @Column(nullable = false)
    private String height;

    @Column(nullable = false)
    private String weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "modifier_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member modifier;

    @OneToMany(
            mappedBy = "item",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<Image> thumbnail;

    @OneToMany(
            mappedBy = "item",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )

    private List<Attachment> attachments;

    @Column(nullable = false)
    private Boolean tempsave;

    @Column(nullable = false)
    private Boolean revise_progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Color color;

    @OneToMany(
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ItemMaterial> materials;

    @OneToMany(
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ItemManufacture> manufactures;

    @Column
    private int revision;

    /**
     * Item??? Attachment ????????? ?????? ?????????
     * @param name
     * @param type
     * @param itemNumber
     * @param width
     * @param height
     * @param weight
     * @param member
     * @param tempsave
     * @param revise_progress
     * @param color
     * @param thumbnail
     * @param attachments
     * @param materials
     * @param manufactures
     * @param partnumbers
     */
    public Item(
            String name,
            String type,
            Integer itemNumber,
            String width,
            String height,
            String weight,
            Member member,
            Boolean tempsave,
            Boolean revise_progress,

            Color color,

            List<Image> thumbnail,

            List<Attachment> attachments,

            List<Material> materials,

            List<Manufacture> manufactures,
            List<String> partnumbers


    ) {
        this.name = name;
        this.type = type;
        this.itemNumber = itemNumber;
        this.width = width;
        this.height = height;
        this.member = member;
        this.weight = weight;
        this.tempsave = tempsave;
        this.revise_progress = revise_progress;

        this.color = (color);

        this.thumbnail = new ArrayList<>();
        addImages(thumbnail);

        this.attachments = new ArrayList<>();
        addAttachments(attachments);

        this.materials =
                materials.stream().map(
                                r -> new ItemMaterial(
                                        this, r)
                        )
                        .collect(toList());

        this.manufactures =
                manufactures.stream().map(

                                //????????? ????????? ????????? ??????
                                r -> new ItemManufacture(
                                        this, r, partnumbers.get(manufactures.indexOf(r))
                                )
                        )
                        .collect(toList());

        this.revision = 65;

    }

    /**
     * Item??? attachment ???????????? ?????? ??? ?????????
     * @param name
     * @param type
     * @param itemNumber
     * @param width
     * @param height
     * @param weight
     * @param member
     * @param tempsave
     * @param revise_progress
     * @param color
     * @param thumbnail
     * @param materials
     * @param manufactures
     * @param partnumbers
     */
    public Item(
            String name,
            String type,
            Integer itemNumber,
            String width,
            String height,
            String weight,
            Member member,
            Boolean tempsave,
            Boolean revise_progress,

            Color color,

            List<Image> thumbnail,


            List<Material> materials,

            List<Manufacture> manufactures,
            List<String> partnumbers


    ) {
        this.name = name;
        this.type = type;
        this.itemNumber = itemNumber;
        this.width = width;
        this.height = height;
        this.member = member;
        this.weight = weight;
        this.tempsave = tempsave;
        this.revise_progress = revise_progress;

        this.color = (color);

        this.thumbnail = new ArrayList<>();
        addImages(thumbnail);



        this.materials =
                materials.stream().map(
                                r -> new ItemMaterial(
                                        this, r)
                        )
                        .collect(toList());

        this.manufactures =
                manufactures.stream().map(

                                //????????? ????????? ????????? ??????
                                r -> new ItemManufacture(
                                        this, r, partnumbers.get(manufactures.indexOf(r))
                                )
                        )
                        .collect(toList());

        this.revision = 65;

    }



    /**
     * postupdaterequest ????????? update ??????
     *
     * @param req
     * @return ?????? ????????? ?????????
     */
    public FileUpdatedResult update(
            ItemUpdateRequest req,
            ColorRepository colorRepository,
            MemberRepository memberRepository,
            MaterialRepository materialRepository,
            ManufactureRepository manufactureRepository,
            ItemManufactureRepository itemManufactureRepository,
            ItemMaterialRepository itemMaterialRepository
    ) {
        AtomicInteger k = new AtomicInteger();

        //TODO update??? ??? ???????????? ?????? ??? ????????? ?????? ?????? ????????? => fix needed
        //isBlank ??? isNull??? ???????????? ?????? ??? / req ??? ????????????
        this.name = req.getName();
        this.type = req.getType();
        this.width = req.getWidth();
        this.height = req.getHeight();
        this.weight = req.getWeight();

        this.color = req.getColorId()==null?null:colorRepository.findById(Long.valueOf(req.getColorId()))
                .orElseThrow(ColorNotFoundException::new);
//
//        if (req.getMaterials().size()>0) {
//
////            for(ItemMaterial itemMaterial : this.materials){
////                ItemMaterial clearedItemMaterial =
////                        itemMaterialRepository.findByItemAndMaterial(
////                        itemMaterial.getItem(), itemMaterial.getMaterial()
////                );
////                itemMaterialRepository.delete(clearedItemMaterial);
////            }
//
//            this.materials.clear();
//
//            this.materials.addAll(
//                    req.getMaterials().stream().map(
//                                    i ->
//                                            materialRepository.
//                                                    findById(i).orElseThrow(MaterialNotFoundException::new)
//                            ).collect(
//                                    toList()
//                            )
//                            .stream().map(
//                                    r -> new ItemMaterial(
//                                            this, r)
//                            )
//                            .collect(toList())
//            );
//        }
//
//
//        if(req.getManufactures().size()>0){
//
////            for(ItemManufacture itemManufacture : this.manufactures){
////                ItemManufacture clearedItemManufacture =
////                        itemManufactureRepository.findByItemAndManufacture(
////                                itemManufacture.getItem(), itemManufacture.getManufacture()
////                        );
////                itemManufactureRepository.delete(clearedItemManufacture);
////            }
//
//            this.manufactures.clear();
//
//
//            this.manufactures.addAll(
//                    req.getManufactures().stream().map(
//                                    i ->
//                                            manufactureRepository.
//                                                    findById(i).orElseThrow(ManufactureNotFoundException::new)
//                            ).collect(
//                                    toList()
//                            ).stream().map(
//
//                                    //????????? ????????? ????????? ??????
//                                    r -> new ItemManufacture(
//                                            this, r, req.getPartnumbers().
//                                            get(k.getAndIncrement())
//                                    )
//                            )
//                            .collect(toList())
//                    );
//        }



        ImageUpdatedResult resultImage =
                findImageUpdatedResult(
                        req.getAddedImages(),
                        req.getDeletedImages()
                );

        addImages(resultImage.getAddedImages());
        deleteImages(resultImage.getDeletedImages());

        AttachmentUpdatedResult resultAttachment =

                findAttachmentUpdatedResult(
                        req.getAddedAttachments(),
                        req.getDeletedAttachments()
                );
        addUpdatedAttachments(req, resultAttachment.getAddedAttachments());

        deleteAttachments(resultAttachment.getDeletedAttachments());

        FileUpdatedResult fileUpdatedResult = new FileUpdatedResult(resultAttachment,resultImage);

        this.modifier =
                memberRepository.findById(
                        req.getModifierId()
                ).orElseThrow(MemberNotFoundException::new);//05 -22 ????????? ??????



        return fileUpdatedResult;
    }

    /**
     * ????????? ?????????
     *
     * @param added
     */
    private void addImages(List<Image> added) {
        added.stream().forEach(i -> {
            thumbnail.add(i);
            i.initItem(this);
        });
    }

    /**
     * ????????? attachments
     *
     * @param added
     */
    private void addAttachments(List<Attachment> added) {
        added.stream().forEach(i -> {
            attachments.add(i);
            i.initItem(this);
        });
    }

    private void addUpdatedAttachments(ItemUpdateRequest req, List<Attachment> added) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();

        added.stream().forEach(i -> {
            attachments.add(i);
            i.initItem(this);
            i.setAttach_comment(req.getAddedAttachmentComment().get((added.indexOf(i))));
            i.setTag(req.getAddedTag().get((added.indexOf(i))));
            i.setAttachmentaddress(
                    "src/main/prodmedia/image/" +
                            sdf1.format(now).substring(0,10)
                            + "/"
                            + i.getUniqueName()
            );
        });
    }

    /**
     * ????????? ????????? ?????? (?????? ?????? ????????? ??????)
     *
     * @param deleted
     */
    private void deleteImages(List<Image> deleted) {
        deleted.stream().
                forEach(di ->
                        this.thumbnail.remove(di)
                );
    }


    /**
     * ????????? ????????? ??????
     * (?????? ?????? ????????? ??????)
     * @param deleted
     */
    private void deleteAttachments(List<Attachment> deleted) {
        deleted.stream().forEach(di ->
                        di.setDeleted(true)
                //this.attachments.remove(di)
        );
    }


    /**
     * ???????????? ?????? ??? ????????? ?????? ????????????
     *
     * @param addedImageFiles
     * @param deletedImageIds
     * @return
     */
    private ImageUpdatedResult findImageUpdatedResult(List<MultipartFile> addedImageFiles, List<Long> deletedImageIds) {
        List<Image> addedImages
                = convertImageFilesToImages(addedImageFiles);
        List<Image> deletedImages
                = convertImageIdsToImages(deletedImageIds);
        return new ImageUpdatedResult(addedImageFiles, addedImages, deletedImages);
    }


    private List<Image> convertImageIdsToImages(List<Long> imageIds) {
        return imageIds.stream()
                .map(id -> convertImageIdToImage(id))
                .filter(i -> i.isPresent())
                .map(i -> i.get())
                .collect(toList());
    }

    private Optional<Image> convertImageIdToImage(Long id) {
        return this.thumbnail.stream().filter(i -> i.getId().equals(id)).findAny();
    }

    private List<Image> convertImageFilesToImages(List<MultipartFile> imageFiles) {
        return imageFiles.stream().map(imageFile -> new Image(imageFile.getOriginalFilename())).collect(toList());
    }

    /**
     * ???????????? ?????? ??? ????????? ?????? ????????????
     *
     * @return
     */
    private AttachmentUpdatedResult findAttachmentUpdatedResult(
            List<MultipartFile> addedAttachmentFiles,
            List<Long> deletedAttachmentIds
    ) {
        List<Attachment> addedAttachments
                = convertAttachmentFilesToAttachments(addedAttachmentFiles);
        List<Attachment> deletedAttachments
                = convertAttachmentIdsToAttachments(deletedAttachmentIds);
        return new AttachmentUpdatedResult(addedAttachmentFiles, addedAttachments, deletedAttachments);
    }


    private List<Attachment> convertAttachmentIdsToAttachments(List<Long> attachmentIds) {
        return attachmentIds.stream()
                .map(id -> convertAttachmentIdToAttachment(id))
                .filter(i -> i.isPresent())
                .map(i -> i.get())
                .collect(toList());
    }

    private Optional<Attachment> convertAttachmentIdToAttachment(Long id) {
        return this.attachments.stream().filter(i -> i.getId().equals(id)).findAny();
    }

    private List<Attachment> convertAttachmentFilesToAttachments(List<MultipartFile> attachmentFiles) {
        return attachmentFiles.stream().map(attachmentFile -> new Attachment(
                attachmentFile.getOriginalFilename()
        )).collect(toList());
    }


    /**
     * ???????????? ?????? ???????????? ????????? ????????? ???????????? ??????
     * ??? ?????? ???????????? ????????? ?????? ?????? ???????????????
     * ????????? ????????? ?????????, ????????? ????????? ??????
     */
    @Getter
    @AllArgsConstructor
    public static class ImageUpdatedResult {
        private List<MultipartFile> addedImageFiles;
        private List<Image> addedImages;
        private List<Image> deletedImages;
    }

    /**
     * ???????????? ?????? ???????????? ????????? ????????? ???????????? ??????
     * ??? ?????? ???????????? ????????? ?????? ?????? ???????????????
     * ????????? ?????? ?????????, ????????? ?????? ?????? => ?????? ???????????? ??????
     */
    @Getter
    @AllArgsConstructor
    public static class AttachmentUpdatedResult {
        private List<MultipartFile> addedAttachmentFiles;
        private List<Attachment> addedAttachments;
        private List<Attachment> deletedAttachments;
    }

    @Getter
    @AllArgsConstructor
    public static class FileUpdatedResult {
        private AttachmentUpdatedResult attachmentUpdatedResult;
        private ImageUpdatedResult imageUpdatedResult;
    }


}
