using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addUniqueCattendance : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_c_attendance_companyId_enumber_workDate",
                table: "c_attendance",
                columns: new[] { "companyId", "enumber", "workDate" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_c_attendance_companyId_enumber_workDate",
                table: "c_attendance");
        }
    }
}
