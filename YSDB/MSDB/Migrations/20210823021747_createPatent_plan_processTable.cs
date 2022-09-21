using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createPatent_plan_processTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "process",
                table: "patent_plan");

            migrationBuilder.CreateTable(
                name: "patent_plan_process",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    patentPlanId = table.Column<int>(nullable: false),
                    process = table.Column<int>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    createTime = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_plan_process", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "patent_plan_process");

            migrationBuilder.AddColumn<int>(
                name: "process",
                table: "patent_plan",
                nullable: false,
                defaultValue: 0);
        }
    }
}
