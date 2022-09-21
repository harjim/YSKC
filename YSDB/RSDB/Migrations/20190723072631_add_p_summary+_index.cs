using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class add_p_summary_index : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_p_summary_projectId_month_rdType",
                table: "p_summary",
                columns: new[] { "projectId", "month", "rdType" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_summary_projectId_month_rdType",
                table: "p_summary");
        }
    }
}
