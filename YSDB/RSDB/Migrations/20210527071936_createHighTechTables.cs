using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createHighTechTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "highTech",
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
                    companyId = table.Column<int>(nullable: false),
                    tecIndustry = table.Column<string>(maxLength: 20, nullable: false),
                    hcode = table.Column<string>(maxLength: 20, nullable: false),
                    hname = table.Column<string>(maxLength: 200, nullable: false),
                    startYear = table.Column<int>(nullable: false),
                    endYear = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_highTech", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "highTech_detail",
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
                    companyId = table.Column<int>(nullable: false),
                    highTechId = table.Column<int>(nullable: false),
                    hasSameTechStandard = table.Column<bool>(nullable: false),
                    techTtandard = table.Column<string>(maxLength: 20, nullable: true),
                    otherTechStandard = table.Column<string>(maxLength: 20, nullable: true),
                    hasQualityStandard = table.Column<bool>(nullable: false),
                    qualityStandard = table.Column<string>(maxLength: 50, nullable: true),
                    passTest = table.Column<bool>(nullable: false),
                    techLevel = table.Column<int>(nullable: false),
                    domesticFirst = table.Column<bool>(nullable: false),
                    internationalLevel = table.Column<int>(nullable: false),
                    projectSource = table.Column<int>(nullable: false),
                    techSource = table.Column<int>(nullable: false),
                    devType = table.Column<int>(nullable: false),
                    advancedExplain = table.Column<string>(maxLength: 2000, nullable: true),
                    patents = table.Column<string>(maxLength: 1000, nullable: true),
                    patentsTech = table.Column<string>(maxLength: 2000, nullable: true),
                    techCompare = table.Column<string>(maxLength: 2000, nullable: true),
                    advantage = table.Column<string>(maxLength: 2000, nullable: true),
                    evaluate = table.Column<string>(maxLength: 2000, nullable: true),
                    contribution = table.Column<string>(maxLength: 2000, nullable: true),
                    ownerProperty = table.Column<int>(nullable: false),
                    innovation = table.Column<int>(nullable: false),
                    advanced = table.Column<int>(nullable: false),
                    maturity = table.Column<int>(nullable: false),
                    award = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_highTech_detail", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "highTech_file",
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
                    companyId = table.Column<int>(nullable: false),
                    highTechId = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false),
                    filepath = table.Column<string>(maxLength: 500, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_highTech_file", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "highTech_product",
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
                    companyId = table.Column<int>(nullable: false),
                    highTechId = table.Column<int>(nullable: true),
                    productName = table.Column<string>(maxLength: 200, nullable: false),
                    bookDate = table.Column<DateTime>(nullable: false),
                    voucherNo = table.Column<string>(maxLength: 50, nullable: false),
                    quantity = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    unitPrice = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    income = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    customer = table.Column<string>(maxLength: 100, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_highTech_product", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "highTech_project",
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
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    highTechId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_highTech_project", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "highTech");

            migrationBuilder.DropTable(
                name: "highTech_detail");

            migrationBuilder.DropTable(
                name: "highTech_file");

            migrationBuilder.DropTable(
                name: "highTech_product");

            migrationBuilder.DropTable(
                name: "highTech_project");
        }
    }
}
