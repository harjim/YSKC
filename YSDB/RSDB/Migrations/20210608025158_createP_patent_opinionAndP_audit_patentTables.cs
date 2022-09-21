using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_patent_opinionAndP_audit_patentTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "filepath",
                table: "p_patent_plan",
                maxLength: 1000,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "inventor",
                table: "p_patent_plan",
                maxLength: 200,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "p_audit_patent",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    patentPlanId = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    suggestion = table.Column<string>(maxLength: 2000, nullable: true),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_audit_patent", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_patent_opinion",
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
                    patentPlanId = table.Column<int>(nullable: false),
                    filepath = table.Column<string>(maxLength: 1000, nullable: true),
                    opinion = table.Column<string>(maxLength: 2000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_patent_opinion", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_audit_patent");

            migrationBuilder.DropTable(
                name: "p_patent_opinion");

            migrationBuilder.DropColumn(
                name: "filepath",
                table: "p_patent_plan");

            migrationBuilder.DropColumn(
                name: "inventor",
                table: "p_patent_plan");
        }
    }
}
