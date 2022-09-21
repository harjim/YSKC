using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createArticleTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_patent_buying_demand_customerId_year",
                table: "patent_buying_demand");

            migrationBuilder.CreateTable(
                name: "article",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    title = table.Column<string>(maxLength: 100, nullable: false),
                    type = table.Column<int>(nullable: false),
                    author = table.Column<string>(maxLength: 50, nullable: false),
                    source = table.Column<string>(maxLength: 100, nullable: true),
                    content = table.Column<string>(type: "text", nullable: false),
                    issueDate = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_article", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_patent_buying_demand_customerId_year_type",
                table: "patent_buying_demand",
                columns: new[] { "customerId", "year", "type" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "article");

            migrationBuilder.DropIndex(
                name: "IX_patent_buying_demand_customerId_year_type",
                table: "patent_buying_demand");

            migrationBuilder.CreateIndex(
                name: "IX_patent_buying_demand_customerId_year",
                table: "patent_buying_demand",
                columns: new[] { "customerId", "year" },
                unique: true);
        }
    }
}
