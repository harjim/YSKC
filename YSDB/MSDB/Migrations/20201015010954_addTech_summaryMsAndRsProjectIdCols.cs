using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addTech_summaryMsAndRsProjectIdCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_tech_summary_projectId",
                table: "tech_summary");

            migrationBuilder.RenameColumn(
                name: "projectId",
                table: "tech_summary",
                newName: "rsProjectId");

            migrationBuilder.AddColumn<int>(
                name: "msProjectId",
                table: "tech_summary",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_tech_summary_msProjectId",
                table: "tech_summary",
                column: "msProjectId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_tech_summary_msProjectId",
                table: "tech_summary");

            migrationBuilder.DropColumn(
                name: "msProjectId",
                table: "tech_summary");

            migrationBuilder.RenameColumn(
                name: "rsProjectId",
                table: "tech_summary",
                newName: "projectId");

            migrationBuilder.CreateIndex(
                name: "IX_tech_summary_projectId",
                table: "tech_summary",
                column: "projectId",
                unique: true);
        }
    }
}
