using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createC_productTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "fileType",
                table: "p_docFile_attachment",
                nullable: false,
                defaultValue: 99);

            migrationBuilder.CreateTable(
                name: "c_product",
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
                    pname = table.Column<string>(maxLength: 200, nullable: false),
                    pcode = table.Column<string>(maxLength: 20, nullable: false),
                    creationDate = table.Column<DateTime>(nullable: false),
                    model = table.Column<string>(maxLength: 100, nullable: false),
                    unit = table.Column<string>(maxLength: 10, nullable: false),
                    parameter = table.Column<string>(maxLength: 1000, nullable: true),
                    features = table.Column<string>(maxLength: 1000, nullable: true),
                    mainRaw = table.Column<string>(maxLength: 1000, nullable: true),
                    comparison = table.Column<string>(maxLength: 1000, nullable: true),
                    output = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    outputValue = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_product", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "c_product_year",
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
                    productId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    output = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    outputValue = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_c_product_year", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_c_product_year_companyId_productId_year",
                table: "c_product_year",
                columns: new[] { "companyId", "productId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "c_product");

            migrationBuilder.DropTable(
                name: "c_product_year");

            migrationBuilder.DropColumn(
                name: "fileType",
                table: "p_docFile_attachment");
        }
    }
}
