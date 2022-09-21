using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createExpertTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "masterTel",
                table: "t_project",
                maxLength: 30,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 30);

            migrationBuilder.AlterColumn<string>(
                name: "masterName",
                table: "t_project",
                maxLength: 30,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 30);

            migrationBuilder.AlterColumn<string>(
                name: "linkTel",
                table: "t_project",
                maxLength: 30,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 30);

            migrationBuilder.AlterColumn<string>(
                name: "linkName",
                table: "t_project",
                maxLength: 30,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 30);

            migrationBuilder.AlterColumn<DateTime>(
                name: "endDate",
                table: "t_project",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AlterColumn<DateTime>(
                name: "beginDate",
                table: "t_project",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AlterColumn<DateTime>(
                name: "applyDate",
                table: "t_project",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AlterColumn<string>(
                name: "itemName",
                table: "t_product_stage_list",
                maxLength: 100,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 20);

            migrationBuilder.CreateTable(
                name: "e_award",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    eUserId = table.Column<int>(nullable: false),
                    awardProject = table.Column<string>(maxLength: 50, nullable: false),
                    grantDate = table.Column<DateTime>(nullable: false),
                    awardName = table.Column<string>(maxLength: 50, nullable: false),
                    type = table.Column<int>(nullable: false),
                    ranking = table.Column<string>(maxLength: 50, nullable: false),
                    grantDept = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_award", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_bank_info",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    eUserId = table.Column<int>(nullable: false),
                    bankName = table.Column<string>(maxLength: 100, nullable: true),
                    bankAccount = table.Column<string>(maxLength: 50, nullable: true),
                    accountName = table.Column<string>(maxLength: 50, nullable: true),
                    postCode = table.Column<string>(maxLength: 10, nullable: true),
                    remitAddress = table.Column<string>(maxLength: 200, nullable: true),
                    idFront = table.Column<string>(maxLength: 100, nullable: false),
                    idBack = table.Column<string>(maxLength: 100, nullable: false),
                    diplomaPath = table.Column<string>(maxLength: 100, nullable: false),
                    titlePath = table.Column<string>(maxLength: 100, nullable: false),
                    positionPath = table.Column<string>(maxLength: 100, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_bank_info", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_book",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    eUserId = table.Column<int>(nullable: false),
                    bookName = table.Column<string>(maxLength: 50, nullable: false),
                    author = table.Column<string>(maxLength: 50, nullable: false),
                    authorOrder = table.Column<int>(nullable: false),
                    issueDate = table.Column<DateTime>(nullable: false),
                    issueUnit = table.Column<string>(maxLength: 50, nullable: false),
                    bookType = table.Column<string>(maxLength: 50, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_book", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_extra",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    eUserId = table.Column<int>(nullable: false),
                    homePage = table.Column<string>(maxLength: 100, nullable: true),
                    workExp = table.Column<string>(maxLength: 200, nullable: true),
                    workResult = table.Column<string>(maxLength: 200, nullable: true),
                    overseaExp = table.Column<bool>(nullable: false),
                    overseaContent = table.Column<string>(maxLength: 200, nullable: true),
                    highestReward = table.Column<string>(maxLength: 50, nullable: true),
                    academicContent = table.Column<string>(maxLength: 200, nullable: true),
                    keyWord = table.Column<string>(maxLength: 200, nullable: true),
                    specialBenefit = table.Column<string>(maxLength: 200, nullable: true),
                    socialAppointments = table.Column<string>(maxLength: 200, nullable: true),
                    otherGNVQsPath = table.Column<string>(maxLength: 100, nullable: true),
                    otherPath = table.Column<string>(maxLength: 200, nullable: true),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_extra", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_intellectual_property",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    eUserId = table.Column<int>(nullable: false),
                    pname = table.Column<string>(maxLength: 100, nullable: true),
                    type = table.Column<int>(nullable: false),
                    country = table.Column<int>(nullable: false),
                    appNo = table.Column<string>(maxLength: 30, nullable: true),
                    authNo = table.Column<string>(maxLength: 30, nullable: true),
                    patentType = table.Column<int>(nullable: true),
                    transform = table.Column<bool>(nullable: false),
                    issueUnit = table.Column<string>(maxLength: 50, nullable: true),
                    certificateNo = table.Column<string>(maxLength: 30, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_intellectual_property", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_paper",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    eUserId = table.Column<int>(nullable: false),
                    heading = table.Column<string>(maxLength: 50, nullable: false),
                    issueDate = table.Column<DateTime>(nullable: false),
                    journalName = table.Column<string>(maxLength: 50, nullable: false),
                    dataIncluded = table.Column<string>(maxLength: 100, nullable: true),
                    includedCnt = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_paper", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_user",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    username = table.Column<string>(maxLength: 50, nullable: false),
                    password = table.Column<string>(maxLength: 50, nullable: false),
                    realName = table.Column<string>(maxLength: 30, nullable: false),
                    logoPath = table.Column<string>(maxLength: 100, nullable: true),
                    email = table.Column<string>(maxLength: 50, nullable: true),
                    mobilePhone = table.Column<string>(maxLength: 20, nullable: false),
                    idType = table.Column<int>(nullable: false),
                    idNo = table.Column<string>(maxLength: 20, nullable: false),
                    gender = table.Column<int>(nullable: false),
                    birthday = table.Column<DateTime>(nullable: true),
                    eType = table.Column<int>(nullable: false),
                    title = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_user", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_user_info",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    eUserId = table.Column<int>(nullable: false),
                    officeTel = table.Column<string>(maxLength: 30, nullable: true),
                    homeTel = table.Column<string>(maxLength: 30, nullable: true),
                    fax = table.Column<string>(maxLength: 50, nullable: true),
                    postCode = table.Column<string>(maxLength: 10, nullable: true),
                    otherNVQ = table.Column<string>(maxLength: 100, nullable: true),
                    workType = table.Column<string>(maxLength: 50, nullable: true),
                    position = table.Column<string>(maxLength: 50, nullable: true),
                    unitName = table.Column<string>(maxLength: 50, nullable: false),
                    address = table.Column<string>(maxLength: 200, nullable: false),
                    deptName = table.Column<string>(maxLength: 50, nullable: true),
                    unitType = table.Column<int>(nullable: true),
                    workCity = table.Column<string>(maxLength: 50, nullable: false),
                    nation = table.Column<string>(maxLength: 10, nullable: true),
                    eduLevel = table.Column<int>(nullable: true),
                    degree = table.Column<int>(nullable: true),
                    graduatedSchool = table.Column<string>(maxLength: 50, nullable: true),
                    degreeSchool = table.Column<string>(maxLength: 50, nullable: true),
                    graduatedDate = table.Column<DateTime>(nullable: true),
                    major = table.Column<string>(maxLength: 50, nullable: false),
                    doctorLevel = table.Column<string>(maxLength: 50, nullable: true),
                    firstSubject = table.Column<string>(maxLength: 50, nullable: true),
                    secondSubject = table.Column<string>(maxLength: 50, nullable: true),
                    thirdSubject = table.Column<string>(maxLength: 50, nullable: true),
                    industryType = table.Column<string>(maxLength: 10, nullable: false),
                    tradition = table.Column<string>(maxLength: 50, nullable: true),
                    booming = table.Column<string>(maxLength: 50, nullable: true),
                    isGovReview = table.Column<bool>(nullable: false),
                    govReviewName = table.Column<string>(maxLength: 200, nullable: true),
                    other = table.Column<string>(maxLength: 2000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_user_info", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "e_award");

            migrationBuilder.DropTable(
                name: "e_bank_info");

            migrationBuilder.DropTable(
                name: "e_book");

            migrationBuilder.DropTable(
                name: "e_extra");

            migrationBuilder.DropTable(
                name: "e_intellectual_property");

            migrationBuilder.DropTable(
                name: "e_paper");

            migrationBuilder.DropTable(
                name: "e_user");

            migrationBuilder.DropTable(
                name: "e_user_info");

            migrationBuilder.AlterColumn<string>(
                name: "masterTel",
                table: "t_project",
                maxLength: 30,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 30,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "masterName",
                table: "t_project",
                maxLength: 30,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 30,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "linkTel",
                table: "t_project",
                maxLength: 30,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 30,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "linkName",
                table: "t_project",
                maxLength: 30,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 30,
                oldNullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "endDate",
                table: "t_project",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "beginDate",
                table: "t_project",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "applyDate",
                table: "t_project",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "itemName",
                table: "t_product_stage_list",
                maxLength: 20,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 100);
        }
    }
}
