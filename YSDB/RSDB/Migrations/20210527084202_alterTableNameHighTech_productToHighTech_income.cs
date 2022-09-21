using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterTableNameHighTech_productToHighTech_income : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "highTech_product");

            migrationBuilder.RenameColumn(
                name: "techTtandard",
                table: "highTech_detail",
                newName: "techStandard");

            migrationBuilder.CreateTable(
                name: "highTech_income",
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
                    client = table.Column<string>(maxLength: 100, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_highTech_income", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "highTech_income");

            migrationBuilder.RenameColumn(
                name: "techStandard",
                table: "highTech_detail",
                newName: "techTtandard");

            migrationBuilder.CreateTable(
                name: "highTech_product",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    bookDate = table.Column<DateTime>(nullable: false),
                    client = table.Column<string>(maxLength: 100, nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    highTechId = table.Column<int>(nullable: true),
                    income = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    productName = table.Column<string>(maxLength: 200, nullable: false),
                    quantity = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    unitPrice = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    voucherNo = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_highTech_product", x => x.id);
                });
        }
    }
}
